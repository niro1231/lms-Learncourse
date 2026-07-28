import { useState } from "react";
import { addLesson } from "../../service/lessonService";
import "./AddLessonModal.css";


function AddLessonModal({courseId, onClose, onAdded}) {


    const [title,setTitle] = useState("");
    const [file,setFile] = useState(null);



    const handleSubmit = async(e)=>{

        e.preventDefault();


        try{

            const formData = new FormData();

            formData.append("title", title);
            formData.append("file", file);


            await addLesson(courseId, formData);


            onAdded();
            onClose();


        }catch(error){

            console.log(error);
            alert("Lesson creation failed");

        }

    };



    return (

        <div className="modal-backdrop">


            <div className="add-lesson-modal">


                <div className="modal-header">

                    <h3>Add Lesson</h3>

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
                        placeholder="Lesson title"
                        value={title}
                        onChange={(e)=>setTitle(e.target.value)}
                        required
                    />



                    <input
                        type="file"
                        accept=".pdf"
                        onChange={(e)=>setFile(e.target.files[0])}
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
                            Add Lesson
                        </button>


                    </div>


                </form>


            </div>


        </div>

    );
}


export default AddLessonModal;