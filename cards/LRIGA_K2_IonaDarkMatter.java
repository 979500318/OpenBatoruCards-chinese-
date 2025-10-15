package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_IonaDarkMatter extends Card {

    public LRIGA_K2_IonaDarkMatter()
    {
        setImageSets("WXDi-P13-037");

        setOriginalName("イオナ・ダーク・マター");
        setAltNames("イオナダークマター Iona Daaku Mataa");
        setDescription("jp",
                "@E：対戦相手は自分のシグニ１体を選びトラッシュに置く。\n" +
                "@E %K %X %X %X：あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。"
        );

        setName("en", "Iona Dark Matter");
        setDescription("en",
                "@E: Your opponent chooses a SIGNI on their field and puts it into its owner's trash.\n@E %K %X %X %X: Add target card without ## from your trash to your Life Cloth."
        );
        
        setName("en_fan", "Iona Dark Matter");
        setDescription("en_fan",
                "@E: Your opponent chooses 1 of their SIGNI, and puts it into the trash.\n" +
                "@E %K %X %X %X: Target 1 card without ## @[Life Burst]@ from your trash, and add it to life cloth."
        );

		setName("zh_simplified", "伊绪奈·黑暗·物质");
        setDescription("zh_simplified", 
                "@E :对战对手选自己的精灵1只放置到废弃区。\n" +
                "@E %K%X %X %X从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(3)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
            trash(cardIndex);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().not(new TargetFilter().lifeBurst()).fromTrash()).get();
            addToLifeCloth(target);
        }
    }
}
