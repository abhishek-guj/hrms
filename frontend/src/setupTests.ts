import "@testing-library/jest-dom";
import { cleanup } from "@testing-library/react";
import { afterEach, beforeAll, vitest } from "vitest";

afterEach(() => {
	cleanup();
});

beforeAll(() => {
	window.HTMLElement.prototype.hasPointerCapture = vitest.fn();
	window.HTMLElement.prototype.scrollIntoView = vitest.fn();
});
