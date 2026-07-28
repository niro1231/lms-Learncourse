import api from "./api";

export const getLessonsByCourse = (courseId) => {
    return api.get(`/courses/${courseId}/lessons`);
};

export const updateLesson = (courseId, lessonId, data) => {
    return api.put(
        `/courses/${courseId}/lessons/${lessonId}`,
        data,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );
};

export const deleteLesson = (courseId, lessonId) => {
    return api.delete(`/courses/${courseId}/lessons/${lessonId}`);
};

export const addLesson = (courseId, formData) => {
    return api.post(`/courses/${courseId}/lessons`, formData, {
        headers:{
            "Content-Type":"multipart/form-data"
        }
    });
};