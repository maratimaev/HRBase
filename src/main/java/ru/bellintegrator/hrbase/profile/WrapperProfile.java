package ru.bellintegrator.hrbase.profile;

/**
 * Профили JsonView для фильтрации полей объекта Wrapper
 */
public interface WrapperProfile {
    /**
     * JsonView для всех отправляемых данных
     */
    interface Data {}

    /**
     * {@inheritDoc}
     */
    interface OrganizationShort extends Data, OrganizationProfile.Short {}

    /**
     * {@inheritDoc}
     */
    interface OrganizationFull extends Data, OrganizationProfile.Full {}
}
