import { useEffect, useState } from "react";
import { getCourseById, updateCourse } from "../../service/courseService";

function EditCourseModal({ courseId, onClose, onUpdated }) {
    const [form, setForm] = useState({
        title: "",
        description: "",
        category: ""
    });

    const [thumbnail, setThumbnail] = useState(null);
    const [currentThumbnail, setCurrentThumbnail] = useState("");

    useEffect(() => {
        loadCourse();
    }, []);

    const loadCourse = async () => {
        const response = await getCourseById(courseId);

        setForm({
            title: response.data.title,
            description: response.data.description,
            category: response.data.category
        });

        setCurrentThumbnail(response.data.thumbnailUrl);
    };

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();

        formData.append("title", form.title);
        formData.append("description", form.description);
        formData.append("category", form.category);

        if (thumbnail) {
            formData.append("thumbnail", thumbnail);
        }

        await updateCourse(courseId, formData);

        onUpdated();
        onClose();
    };

    return (
        <div
            className="modal d-block"
            style={{ background: "rgba(0,0,0,0.5)" }}
        >
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5>Update Course</h5>
                        <button
                            className="btn-close"
                            onClick={onClose}
                        />
                    </div>

                    <form onSubmit={handleSubmit}>
                        <div className="modal-body">
                            <input
                                className="form-control mb-3"
                                name="title"
                                value={form.title}
                                onChange={handleChange}
                                placeholder="Title"
                            />

                            <textarea
                                className="form-control mb-3"
                                name="description"
                                value={form.description}
                                onChange={handleChange}
                                placeholder="Description"
                            />

                            <input
                                className="form-control mb-3"
                                name="category"
                                value={form.category}
                                onChange={handleChange}
                                placeholder="Category"
                            />

                            {currentThumbnail && (
                                <div className="mb-3 text-center">
                                    <img
                                        src={`http://localhost:8080/${currentThumbnail}`}
                                        alt="Course Thumbnail"
                                        className="img-fluid rounded border"
                                        style={{
                                            width: "100%",
                                            maxHeight: "180px",
                                            objectFit: "cover"
                                        }}
                                    />
                                </div>
                            )}

                            <input
                                type="file"
                                className="form-control"
                                accept="image/*"
                                onChange={(e) =>
                                    setThumbnail(e.target.files[0])
                                }
                            />
                        </div>

                        <div className="modal-footer">
                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={onClose}
                            >
                                Cancel
                            </button>

                            <button
                                type="submit"
                                className="btn btn-primary"
                            >
                                Update
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default EditCourseModal;