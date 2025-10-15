package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_AnneBonnySailorOfDemonicSeas extends Card {

    public SIGNI_B3_AnneBonnySailorOfDemonicSeas()
    {
        setImageSets("WXK01-053");

        setOriginalName("魔海の船員　アンボニー");
        setAltNames("マカイノセンインアンボニー Makai no Senin Anbonii");
        setDescription("jp",
                "@C：あなたの＜悪魔＞のシグニのパワーを＋1000する。\n" +
                "@C：あなたの手札が０枚であるかぎり、あなたの＜悪魔＞のシグニのパワーを＋2000する。"
        );

        setName("en", "Anne Bonny, Sailor of Demonic Seas");
        setDescription("en",
                "@C: All of your <<Devil>> SIGNI get +1000 power.\n" +
                "@C: As long as there are 0 cards in your hand, all of your <<Devil>> SIGNI get +2000 power."
        );

		setName("zh_simplified", "魔海的船员 安妮伯妮");
        setDescription("zh_simplified", 
                "@C :你的<<悪魔>>精灵的力量+1000。\n" +
                "@C :你的手牌在0张时，你的<<悪魔>>精灵的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL), new PowerModifier(1000));
            registerConstantAbility(this::onConstEff2Cond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL), new PowerModifier(2000));
        }
        
        private ConditionState onConstEff2Cond()
        {
            return getHandCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
