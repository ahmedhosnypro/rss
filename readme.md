## RSS/Atom feeds update tracker

This is a microservice that tracks changes of RSS/Atom feeds configured in its database. By tracking we mean that every new item appeared in a feed is stored in a dedicated table. The following fields are stored:

* Title
* Link
* Description
* Publication date
* Author
* Thumbnails
* Categories
* Related identifiers

The service has a REST API that exposes the following endpoints:

Feed Configuration:
* `/api/v1/feeds`: Get a list of all configured feeds.
* `/api/v1/feeds/add`: Add a new feed configuration.
* `/api/v1/feeds/{id}/enable`: Enable a feed.
* `/api/v1/feeds/{id}/disable`: Disable a feed.

Feed Categories
* `/api/v1/categories`: Get a list of all categories.

Feed Entries
* `/posts`: Get a list of items for a given feed.
* `/posts/{id}`: Get an item by ID.
* `/posts/category/{category}`: Get a list of items for a given category.
* `/posts/?categories={categories}&startDate={startDate}&endDate={endDate}`: Search for items by categories and date range.