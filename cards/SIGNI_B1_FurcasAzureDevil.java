package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_FurcasAzureDevil extends Card {

    public SIGNI_B1_FurcasAzureDevil()
    {
        setImageSets("WX24-P1-067");

        setOriginalName("蒼魔　フルカス");
        setAltNames("ソウマフルカス Souma Furukasu");
        setDescription("jp",
                "@U:このシグニがコストか効果によって場からトラッシュに置かれたとき、カードを１枚引く。"
        );

        setName("en", "Furcas, Azure Devil");
        setDescription("en",
                "@U: When this SIGNI is put from the field into the trash by a cost or an effect, draw 1 card."
        );

		setName("zh_simplified", "苍魔 富卡斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果从场上放置到废弃区时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return getEvent().getSourceAbility() != null && cardIndex.isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            draw(1);
        }
    }
}
