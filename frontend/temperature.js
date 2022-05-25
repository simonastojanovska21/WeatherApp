fetch('http://localhost:8080/api/city/all')
    .then(response => response.json())
    .then(async cities => await getForecastForCity(cities));

async function getForecastForCity(cities) {

    for (let i = 0; i < cities.length; i++) {
        await fetch('http://localhost:8080/api/forecast/city/' + cities[i] + '/temperature/25')
            .then(response => response.json())
            .then(forecasts => displayForecasts(forecasts))
    }

}

displayForecasts = forecasts => {
    const forecastDiv = document.querySelector("#temperature");
    const rowElement = document.createElement('div')
    rowElement.classList.add('row')
    rowElement.classList.add('mt-5')
    const cityNameElement = document.createElement('span')
    cityNameElement.classList.add('h1')
    cityNameElement.classList.add('fst-italic')
    cityNameElement.classList.add('pb-3')
    cityNameElement.innerText = forecasts[0].cityName
    rowElement.append(cityNameElement)
    console.log(forecasts)
    forecasts.forEach(forecast => {
        const colElement = document.createElement('div')
        colElement.classList.add('col')
        colElement.classList.add('text-center')

        const dayElement = document.createElement('span')
        dayElement.innerText = moment(new Date(forecast.date)).format("MMMM Do YYYY");
        colElement.append(dayElement)

        const imgElement = document.createElement('img')
        imgElement.src = "http://openweathermap.org/img/wn/"+ forecast.icon + "@2x.png"
        colElement.append(imgElement)

        const temperatureElement = document.createElement('span')
        temperatureElement.innerText = Math.round(forecast.temperature) + '\u00B0'
        colElement.append(temperatureElement)
        colElement.append(document.createElement('br'))

        const weatherElement = document.createElement('span')
        weatherElement.innerText =   forecast.weatherDescription;
        colElement.append(weatherElement)
        colElement.append(document.createElement('br'))

        const temperatureDetailsElement = document.createElement('span')
        temperatureDetailsElement.innerText = 'Min: ' + Math.round(forecast.minTemperature) + '\u00B0' + '  '+
            'Max: ' + Math.round(forecast.maxTemperature) + '\u00B0';
        colElement.append(temperatureDetailsElement)
        rowElement.append(colElement);
    });
    forecastDiv.append(rowElement)
}
