import axios from "axios";

export default axios.create(
    {
        baseURL: "https://teamportfolio.cfapps.us10.hana.ondemand.com/api/v1",
        headers: {
            "Content-type" : "application/json"
        }
    }
);