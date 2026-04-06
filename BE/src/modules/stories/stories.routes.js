"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const db_1 = require("../../database/db");
const authenticate_1 = require("../../middlewares/authenticate");
const router = (0, express_1.Router)();
// Get all series
router.get("/series", async (req, res) => {
    try {
        const series = await (0, db_1.query) `
            SELECT SeriesID, Title, Description, CoverImage, CreatedAt, UpdatedAt
            FROM Series
            ORDER BY CreatedAt DESC
        `;
        res.json(series.recordset);
    }
    catch (e) {
        console.error("GET /series error:", e);
        res.status(500).json({ error: "Failed to fetch series" });
    }
});
// Get series by id
router.get("/series/:id", async (req, res) => {
    try {
        const seriesId = Number(req.params.id);
        if (!seriesId)
            return res.status(400).json({ error: "Invalid series ID" });
        const series = await (0, db_1.query) `
            SELECT SeriesID, Title, Description, CoverImage, CreatedAt, UpdatedAt
            FROM Series
            WHERE SeriesID = ${seriesId}
        `;
        if (series.recordset.length === 0)
            return res.status(404).json({ error: "Series not found" });
        res.json(series.recordset[0]);
    }
    catch (e) {
        console.error("GET /series/:id error:", e);
        res.status(500).json({ error: "Failed to fetch series" });
    }
});
// Create series
router.post("/series", authenticate_1.authenticate, async (req, res) => {
    try {
        const { title, description, coverImage } = req.body || {};
        if (!title)
            return res.status(400).json({ error: "Title is required" });
        await (0, db_1.query) `
            INSERT INTO Series (Title, Description, CoverImage, CreatedAt, UpdatedAt)
            VALUES (${title}, ${description || null}, ${coverImage || null}, GETDATE(), GETDATE())
        `;
        res.json({ message: "Series created successfully" });
    }
    catch (e) {
        console.error("POST /series error:", e);
        res.status(500).json({ error: "Failed to create series" });
    }
});
// Get chapters by series id
router.get("/series/:id/chapters", async (req, res) => {
    try {
        const seriesId = Number(req.params.id);
        if (!seriesId)
            return res.status(400).json({ error: "Invalid series ID" });
        const chapters = await (0, db_1.query) `
            SELECT ChapterID, Title, Content, ChapterNumber, CreatedAt, UpdatedAt
            FROM Chapters
            WHERE SeriesID = ${seriesId}
            ORDER BY ChapterNumber ASC
        `;
        res.json(chapters.recordset);
    }
    catch (e) {
        console.error("GET /series/:id/chapters error:", e);
        res.status(500).json({ error: "Failed to fetch chapters" });
    }
});
// Get chapter by id
router.get("/chapters/:id", async (req, res) => {
    try {
        const chapterId = Number(req.params.id);
        if (!chapterId)
            return res.status(400).json({ error: "Invalid chapter ID" });
        const chapters = await (0, db_1.query) `
            SELECT c.ChapterID, c.Title, c.Content, c.ChapterNumber, c.CreatedAt, c.UpdatedAt, c.SeriesID, s.Title as SeriesTitle
            FROM Chapters c
            INNER JOIN Series s ON c.SeriesID = s.SeriesID
            WHERE c.ChapterID = ${chapterId}
        `;
        if (chapters.recordset.length === 0)
            return res.status(404).json({ error: "Chapter not found" });
        res.json(chapters.recordset[0]);
    }
    catch (e) {
        console.error("GET /chapters/:id error:", e);
        res.status(500).json({ error: "Failed to fetch chapter" });
    }
});
// Create chapter
router.post("/series/:id/chapters", authenticate_1.authenticate, async (req, res) => {
    try {
        const seriesId = Number(req.params.id);
        const { title, content, chapterNumber } = req.body || {};
        if (!seriesId || !title)
            return res.status(400).json({ error: "Series ID and title are required" });
        await (0, db_1.query) `
            INSERT INTO Chapters (SeriesID, Title, Content, ChapterNumber, CreatedAt, UpdatedAt)
            VALUES (${seriesId}, ${title}, ${content || null}, ${chapterNumber || null}, GETDATE(), GETDATE())
        `;
        res.json({ message: "Chapter created successfully" });
    }
    catch (e) {
        console.error("POST /series/:id/chapters error:", e);
        res.status(500).json({ error: "Failed to create chapter" });
    }
});
module.exports = router;