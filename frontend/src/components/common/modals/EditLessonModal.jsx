import { useEffect,useState } from "react";
import { getLessonsByCourse,updateLesson } from "../../../service/lessonService";

function EditLessonModal({courseId,lessonId,onClose,onUpdated}){

    const [form,setForm]=useState({
        title:"",
        file:null
    });

    const [existingPdf,setExistingPdf]=useState("");

    useEffect(()=>{
        loadLesson();
    },[]);

    const loadLesson=async()=>{
        const response=await getLessonsByCourse(courseId);

        const lesson=response.data.find(
            item=>item.id===lessonId
        );

        setForm({
            title:lesson.title,
            file:null
        });

        setExistingPdf(lesson.pdfUrl);
    };

    const handleChange=(e)=>{
        setForm({
            ...form,
            [e.target.name]:e.target.value
        });
    };

    const handleFileChange=(e)=>{
        setForm({
            ...form,
            file:e.target.files[0]
        });
    };

    const handleSubmit=async(e)=>{
        e.preventDefault();

        const data=new FormData();

        data.append(
            "title",
            form.title
        );

        if(form.file){
            data.append(
                "file",
                form.file
            );
        }

        await updateLesson(
            courseId,
            lessonId,
            data
        );

        onUpdated();
        onClose();
    };

    return(
        <div
            className="modal d-block"
            style={{
                background:"rgba(0,0,0,0.5)"
            }}
        >
            <div className="modal-dialog">
                <div className="modal-content">

                    <div className="modal-header">
                        <h5>
                            Update Lesson
                        </h5>

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
                                placeholder="Lesson Title"
                                required
                            />

                            <div className="mb-3">
                                <label className="form-label">
                                    Current PDF
                                </label>

                                <div>
                                    {existingPdf && (
                                        <a
                                            href={`http://localhost:8080/${existingPdf}`}
                                            target="_blank"
                                            rel="noreferrer"
                                        >
                                            View Existing PDF
                                        </a>
                                    )}
                                </div>
                            </div>

                            <label className="form-label">
                                Upload New PDF
                            </label>

                            <input
                                type="file"
                                className="form-control"
                                accept="application/pdf"
                                onChange={handleFileChange}
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

export default EditLessonModal;