
### `Application Description`
Welcome to the Learn Space application. The application makes use of the NASA Video and Image API. The application allows keyword searches. Results are limited to 10-50 currently per search. It is also possible to save links and review them on the user details page.

The URL for the API being used is:
[https://api.nasa.gov/](https://api.nasa.gov/)

To see if the API is working you can paste the following URL into your browser. You should see data being returned.

[https://images-api.nasa.gov/search?q=moon](https://images-api.nasa.gov/search?q=moon)

The specific API and how to use is is defined here:
[https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf)

### `Copyright & Credits`
This application makes use of the NASA Open API for images. Content is displayed under Nasa Copyright. Users are signed in as Guest and can update the guest log in details.

### `Prequisites`
The following should be present to run the application
*	Java 8 - java version "1.8.0_101"
*	Git - Git git version 2.29.2.windows.2
*	Maven -  v 3.3.9
*	Npm 6.14.9
*	Node v14.15.1

### `Git Repositories`

### `ES6 / React JS`
[https://github.com/mikekingkung/nasa-js.git](https://github.com/mikekingkung/nasa-js.git)

### `Java Spring Boot`
[https://github.com/mikekingkung/NASA_REST.git](https://github.com/mikekingkung/NASA_REST.git)

### Clone from github as follows
git clone https://github.com/mikekingkung/nasa-js.git

and

git clone https://github.com/mikekingkung/NASA_REST.git

NB It is necessary to run the spring boot application for the react js application to work.


### `How to start the Spring boot application` 
*	cd to the location of the local git repository where the pom.xml file is located
*	mvn spring-boot:run

You should see the application start in the console logs with 
‘Started ConsumingRestApplication’

### `API to Spring boot application - REST endpoints`

Get Requests
* localhost:8080/getstaticimage/{image}
* localhost:8080/getimages/{searchterm}/{processinglimit}
* localhost:8080/users/getuser/{id}
* localhost:8080/users/getbyusername/{userName}
* localhost:8080/users/getlinksbyusername/{userName}

Post Requests
* localhost:8080/users /register  (body contains User object as post request)
* localhost:8080/users/saveuserlinks (body contains UserLinks object as post request)

### `Example data returned `
localhost:8080/getimages/moon/2
would give back the following:
```json
[
    {
      video: {
                id: "0",
                url: "http://images-assets.nasa.gov/video/NHQ_2019_0311_Go Forward to the Moon/NHQ_2019_0311_Go Forward to the Moon~orig.mp4",
                title: "Go Forward to the Moon",
                description: "NASA is going to the Moon and on to Mars, in a measured, sustainable way. Working with U.S. companies and international partners, NASA will push the boundaries of human exploration forward to the Moon. NASA is working to establish a permanent human presence on the Moon within the next decade to uncover new scientific discoveries and lay the foundation for private companies to build a lunar economy. Right now, NASA is taking steps to begin this next era of exploration. #Moon2Mars Learn more at: https://www.nasa.gov/moontomars"
      }
    },
    {
      image: {
                id: "1",
                url: "http://images-assets.nasa.gov/video/NHQ_2018_0131_Super Blue Moon Lunar Eclipse/NHQ_2018_0131_Super Blue Moon Lunar Eclipse~large.jpg",
                title: "Super Blue Moon Lunar Eclipse",
                description: "NASA TV provided coverage of Super Blue Moon Lunar Eclipse on Jan. 31. The full moon was the third in a series of “supermoons,” when the Moon is closer to Earth in its orbit -- known as perigee -- and about 14 percent brighter than usual. It was also the second full moon of the month, commonly known as a “blue moon.” As the super blue moon passed through Earth’s shadow, viewers in some locations experienced a total lunar eclipse. While in Earth’s shadow, the moon also took on a reddish tint – which is sometimes referred to as a “blood moon.”"
            }
    }
]
