import React, { useEffect, useState } from 'react'
import './Blog.css'
import Validation from './Validation'
import axios from 'axios'

export const Blog2 = () => {

    const [Loading, setLoading] = useState(false);
    const [response, setResponse] = useState('');
    const [data, setData] = useState({
        input: '',
        blog_style: '',
        words: ''
    });

    const [errors, setErrors] = useState({});

    const handleInput = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const validationErrors = Validation(data);
        setErrors(validationErrors);

        // Proceed only if there are no validation errors
        if (Object.keys(validationErrors).length === 0) {
            setLoading(true);
            try {
                // Example of an API call
                const response = await axios.post('/your-endpoint', data);
                setResponse(response.data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        } else {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="container">
                <div className="header">
                    <h1>Blog Generator</h1>
                    <div className="icon">
                        <i className="fa-solid fa-blog"></i>
                    </div>
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="topic">
                        <label htmlFor="topic">Enter the blog topic</label>
                        <input 
                            type="text" 
                            placeholder="Blog Topic" 
                            id="topic" 
                            name="input" 
                            onChange={handleInput} 
                        />
                        {errors.input && <p>{errors.input}</p>}
                    </div>
                    <div className="words">
                        <label htmlFor="noofwords">No of Words</label>
                        <select name="words" id="noofwords" onChange={handleInput}>
                            <option value="150">150</option>
                            <option value="200">200</option>
                            <option value="300">300</option>
                            <option value="400">400</option>
                        </select>
                    </div>
                    <div className="blog_style">
                        <label htmlFor="profile">Writing the Blog for</label>
                        <select name="blog_style" id="profile" onChange={handleInput}>
                            <option value="Researchers">Researchers</option>
                            <option value="Data Scientists">Data Scientists</option>
                            <option value="Common People">Common People</option>
                            <option value="Freshers">Freshers</option>
                        </select>
                    </div>
                    <div className="generate">
                        <button type="submit">Generate</button>
                    </div>
                </form>
            </div>

            {Loading && (
                <div className="answer">
                    <div className="content">
                        {/* Placeholder content for the blog */}
                        {response || "Loading content..."}
                    </div>
                </div>
            )}
        </>
    );
}
