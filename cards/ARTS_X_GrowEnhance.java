package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_GrowEnhance extends Card {

    public ARTS_X_GrowEnhance()
    {
        setImageSets("WX24-P4-036");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("グロウ・エンハンス");
        setAltNames("グロウエンハンス Gurou Enhansu");
        setDescription("jp",
                "あなたのルリグゾーンに【リミットアッパー】１つを置く。このゲームの間、あなたは以下の能力を得る。" +
                "@>@U：あなたのセンタールリグがグロウしたとき、カードを１枚引く。"
        );

        setName("en", "Grow Enhance");
        setDescription("en",
                "Put 1 [[Limit Upper]] on your LRIG zone. This game, you gain:" +
                "@>@U: Whenever your center LRIG grows, draw 1 card."
        );

		setName("zh_simplified", "成长·突破");
        setDescription("zh_simplified", 
                "你的分身区放置[[界限高地]]1个。这场游戏期间，你得到以下的能力。\n" +
                "@>@U 当你的核心分身成长时，抽1张牌。@@\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());

            AutoAbility attachedAuto = new AutoAbility(GameEventId.GROW, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getLocation() == CardLocation.LRIG ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}

