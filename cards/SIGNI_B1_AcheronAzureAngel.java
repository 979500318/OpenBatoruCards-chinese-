package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B1_AcheronAzureAngel extends Card {

    public SIGNI_B1_AcheronAzureAngel()
    {
        setImageSets("WX24-P2-076");

        setOriginalName("蒼天　アケローン");
        setAltNames("ソウテンアケローン Souten Akeroon");
        setDescription("jp",
                "@C $TP：あなたの手札が４枚以上あるかぎり、あなたのシグニのパワーを＋2000する。"
        );

        setName("en", "Acheron, Azure Angel");
        setDescription("en",
                "@C $TP: As long as there are 4 or more cards in your hand, this SIGNI gets +2000 power."
        );

		setName("zh_simplified", "苍天 阿刻戎");
        setDescription("zh_simplified", 
                "@C $TP :你的手牌在4张以上时，你的精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000));
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
