import api from "./api";

// Register
export const registerUser = (user) => {
    return api.post("/users/register", user, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
};

// Login
export const loginUser = (user)=>{
    return api.post("/users/login", user);
};