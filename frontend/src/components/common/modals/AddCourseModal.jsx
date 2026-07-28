import { useState } from "react";
import { createCourse } from "../../../service/courseService";
import "./AddCourseModal.css";

function AddCourseModal({ onClose, onAdded }) {

    const [formData, setFormData] = useState({
        title: "",
        description: "",
        category: "",
        thumbnail: null
    });


    const handleChange = (e) => {

        if (e.target.name === "thumbnail") {

            setFormData({
                ...formData,
                thumbnail: e.target.files[0]
            });

        } else {

            setFormData({
                ...formData,
                [e.target.name]: e.target.value
            });

        }
    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const data = new FormData();

            data.append("title", formData.title);
            data.append("description", formData.description);
            data.append("category", formData.category);
            data.append("thumbnail", formData.thumbnail);


            await createCourse(data);


            onAdded();   // reload courses
            onClose();   // close modal


        } catch (error) {

            console.log(error);
            alert("Course creation failed");

        }

    };


    return (

        <div className="modal-backdrop">

            <div className="add-course-modal">


                <div className="modal-header">

                    <h3>Add Course</h3>

                    <button
                        type="button"
                        onClick={onClose}
                    >
                        ✕
                    </button>

                </div>



                <form onSubmit={handleSubmit}>


                    <input
                        type="text"
                        name="title"
                        placeholder="Course title"
                        value={formData.title}
                        onChange={handleChange}
                        required
                    />



                    <textarea
                        name="description"
                        placeholder="Course description"
                        value={formData.description}
                        onChange={handleChange}
                        required
                    />



                    <input
                        type="text"
                        name="category"
                        placeholder="Category"
                        value={formData.category}
                        onChange={handleChange}
                        required
                    />



                    <input
                        type="file"
                        name="thumbnail"
                        accept="image/*"
                        onChange={handleChange}
                        required
                    />



                    <div className="modal-footer">


                        <button
                            type="button"
                            onClick={onClose}
                        >
                            Cancel
                        </button>



                        <button
                            type="submit"
                            className="save-btn"
                        >
                            Create
                        </button>


                    </div>


                </form>


            </div>

        </div>

    );
}

export default AddCourseModal;