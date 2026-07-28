import api from "./api";

export const getAdminDashboard=()=>{
    return api.get("/admin/dashboard");
};

export const getAllUsers=()=>{
    return api.get("/admin/users");
};

export const deleteUser=(id)=>{
    return api.delete(`/admin/users/${id}`);
};

export const deleteCourse=(id)=>{
    return api.delete(`/admin/courses/${id}`);
};

export const getAllCourses = () => {
    return api.get("/courses");
};
export const uploadAdvertisement = (formData) => {
    return api.post("/advertisements/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
};