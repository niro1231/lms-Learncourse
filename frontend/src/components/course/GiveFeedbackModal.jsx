import { useState } from "react";
import { addRating } from "../../service/courseService";
import "./GiveFeedbackModal.css";

function GiveFeedbackModal({ courseId, onClose }) {

    const [rating, setRating] = useState(5);
    const [comment, setComment] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await addRating(courseId, {
                rating: Number(rating),
                comment
            });

            alert("Thank you for your feedback!");
            onClose();

        } catch (error) {
            console.log(error);
            alert("Failed to submit feedback.");
        }
    };

    return (
        <div className="modal-backdrop">

            <div className="feedback-modal">

                <div className="modal-header">
                    <h3>Course Feedback</h3>

                    <button
                        type="button"
                        onClick={onClose}
                    >
                        ✕
                    </button>
                </div>

                <form onSubmit={handleSubmit}>

                    <label>Rating</label>

                    <select
                        value={rating}
                        onChange={(e) => setRating(e.target.value)}
                    >
                        <option value={5}>⭐⭐⭐⭐⭐ (5)</option>
                        <option value={4}>⭐⭐⭐⭐ (4)</option>
                        <option value={3}>⭐⭐⭐ (3)</option>
                        <option value={2}>⭐⭐ (2)</option>
                        <option value={1}>⭐ (1)</option>
                    </select>

                    <textarea
                        placeholder="Write your feedback..."
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        required
                    />

                    <div className="modal-footer">

                        <button
                            type="button"
                            className="cancel-btn"
                            onClick={onClose}
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="save-btn"
                        >
                            Submit
                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default GiveFeedbackModal;