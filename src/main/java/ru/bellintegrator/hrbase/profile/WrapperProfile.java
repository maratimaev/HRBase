package ru.bellintegrator.hrbase.profile;

/**
 * Профили JsonView для фильтрации полей объекта Wrapper
 */
public interface WrapperProfile {
    /**
     * JsonView для всех отправляемых данных
     */
    interface Data { }

    /**
     * {@inheritDoc}
     */
    interface Short extends Data, Profile.Short { }

    /**
     * {@inheritDoc}
     */
    interface Full extends Data, Profile.Full { }
}
