---
name: Backlog item
about: Report a problem or propose a change, with the detail needed to implement it
title: 'Sample: add a status filter to the events list'
labels: ''
assignees: ''

---

## Description

The events list must accept a status filter, so that editors can find events that are not yet
published. This fixture is used by the validator self-test and is not a real backlog item.

### Context

- `endurancetrio-app/src/main/java/com/endurancetrio/app/event/web/EventWebController.java` — the
  `getEvents(...)` method serves the searchable directory at `/{language:en|pt}/events`.

### Scope

- Add a `status` request parameter to `EventWebController.getEvents(...)`.

### Out of scope

- Changes to the event detail pages.

### Approach

The filter is applied in the service layer rather than in the template, so the rule can be tested
in isolation. A template-side filter was rejected because it would send unpublished rows to the
browser before hiding them.

### Implementation notes

Follow the naming rules in `docs/development.md`. Add every new message key to both
`endurancetrio-app/src/main/resources/messages.properties` and
`endurancetrio-app/src/main/resources/messages_pt.properties`.

### Testing

Add Mockito unit tests for the new parameter in `EventWebControllerTest`.

### Dependencies

None.

## Acceptance Criteria

- [ ] The events list accepts a `status` request parameter.
- [ ] Unpublished events are hidden from the public events list.
