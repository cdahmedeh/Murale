package net.cdahmedeh.murale.provider.impl.wallhaven;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.ivkos.wallhaven4j.models.misc.enums.Category.GENERAL;
import static com.ivkos.wallhaven4j.models.misc.enums.Order.DESC;
import static com.ivkos.wallhaven4j.models.misc.enums.Purity.SFW;
import static com.ivkos.wallhaven4j.models.misc.enums.Sorting.RANDOM;
import static net.cdahmedeh.murale.util.CollectionUtil.join;
import static net.cdahmedeh.murale.util.CollectionUtil.map;
import static net.cdahmedeh.murale.util.CollectionUtil.truncate;

import java.util.List;
import java.util.Set;

import com.ivkos.wallhaven4j.Wallhaven;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;
import com.ivkos.wallhaven4j.models.misc.enums.ToplistRange;
import com.ivkos.wallhaven4j.util.exceptions.ConnectionException;
import com.ivkos.wallhaven4j.util.exceptions.LoginException;
import com.ivkos.wallhaven4j.util.exceptions.ParseException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotAccessibleException;
import com.ivkos.wallhaven4j.util.exceptions.ResourceNotFoundException;
import com.ivkos.wallhaven4j.util.searchquery.SearchQuery;
import com.ivkos.wallhaven4j.util.searchquery.SearchQueryBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cdahmedeh.murale.domain.Wallpaper;
import net.cdahmedeh.murale.error.ConnectivityException;
import net.cdahmedeh.murale.error.ProviderException;
import net.cdahmedeh.murale.provider.Provider;

@ToString
public class WallhavenProvider extends Provider {
	private static final int WALLPAPERS_PER_PAGE = 24;
	
	@Getter @Setter
	private List<String> keywords = newArrayList();
	
	@Getter @Setter
	private Set<Category> categories = newHashSet(GENERAL);
	
	@Getter @Setter
	private Set<Purity> purities = newHashSet(SFW);
	
	@Getter @Setter
	private Sorting sorting = RANDOM;
	
	@Getter @Setter
	private Order order = DESC;
	
	@Getter @Setter
	private ToplistRange topListRange = null;
	
	@Override
	public String getName() {
		return "wallhaven.cc";
	}
	
	@Override
	public List<Wallpaper> query(final int count) throws ConnectivityException, ProviderException {
		try {
			Wallhaven wh = new Wallhaven();
			
			SearchQuery query = new SearchQueryBuilder()
					.keywords(keywords)
					.categories(categories)
					.purity(purities)
					.sorting(sorting)
					.order(order)
					.pages(count/WALLPAPERS_PER_PAGE + 1)
					.build();
			
			List<com.ivkos.wallhaven4j.models.wallpaper.Wallpaper> results = wh.search(query);
			
			List<Wallpaper> wallpapers = map(results, this::mapWhWallpaper);
			truncate(wallpapers, count);
			
			return wallpapers;
		} catch (ConnectionException e) {
			throw new ConnectivityException(e);
		} catch (LoginException | ParseException | ResourceNotAccessibleException | ResourceNotFoundException e) {
			throw new ProviderException(e);
		}
	}

	private Wallpaper mapWhWallpaper(com.ivkos.wallhaven4j.models.wallpaper.Wallpaper whWallpaper) {
		Wallpaper wallpaper = new Wallpaper();
		
		wallpaper.setId("wh-" + whWallpaper.getId().toString());
		wallpaper.setOrigin(whWallpaper.getUrl());
		wallpaper.setUrl(whWallpaper.getImageUrl());
		wallpaper.setTitle(join(whWallpaper.getTags(), t -> t.toString(), ","));
		
		try {
			wallpaper.setAuthor(whWallpaper.getUser().getUsername());
		} catch (ParseException e) {
			wallpaper.setAuthor("Anonymous");
		}
		
		return wallpaper;
	}
}