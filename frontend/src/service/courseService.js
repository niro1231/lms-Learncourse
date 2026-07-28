import api from "./api";

export const getAllCourses = () => {
    return api.get("/courses");
};
export const getTrendingCourses = () => {
    return api.get("/courses/sort/rating");
};
export const getCourseById = (id) => {
    return api.get(`/courses/${id}`);
};
export const getMyCourses = () => {
    return api.get("/courses/my-courses");
};
export const updateCourse = (id, data) => {
    return api.put(
        `/courses/${id}`,
        data,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );
};
export const deleteCourse = (id) => {
    return api.delete(`/courses/${id}`);
};

export const createCourse = (formData) => {
    return api.post("/courses", formData, {
        headers:{
            "Content-Type":"multipart/form-data"
        }
    });
};
export const addRating = (courseId, data) => {
    return api.post(`/courses/${courseId}/ratings`, data);
};