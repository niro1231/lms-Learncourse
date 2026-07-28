import axios from "axios";

const API_URL="http://localhost:8080/api/advertisements";

export const getAdvertisements=()=>axios.get(API_URL);