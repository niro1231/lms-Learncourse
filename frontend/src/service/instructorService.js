import api from "./api";

export const getAllInstructors = () => {
    return api.get("/instructors");
};

export const getInstructorDashboard = () => {
    return api.get("/instructor/dashboard");
};
export const getCoursesByInstructor = (instructorId) => {
    return api.get(`/courses/instructor/${instructorId}`);
};
// get instructor details by id
export const getInstructorById = (id) => {
    return api.get(`/instructor/${id}`);
};