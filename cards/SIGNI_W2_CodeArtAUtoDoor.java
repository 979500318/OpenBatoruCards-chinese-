package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_CodeArtAUtoDoor extends Card {

    public SIGNI_W2_CodeArtAUtoDoor()
    {
        setImageSets("WXDi-P15-086");

        setOriginalName("コードアート　Ａトドア");
        setAltNames("コードアートエートドア Koodo Aato Eeto Doa");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルが３枚以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@A #D @[手札からスペルを１枚捨てる]@：対戦相手のパワー5000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "A - To Door, Code: Art");
        setDescription("en",
                "@C: As long as there are three or more spells in your trash, this SIGNI gets +4000 power.\n@A #D @[Discard a spell]@: Return target SIGNI on your opponent's field with power 5000 or less to its owner's hand."
        );
        
        setName("en_fan", "Code Art A Uto Door");
        setDescription("en_fan",
                "@C: As long as there are 3 or more spells in your trash, this SIGNI gets +4000 power.\n" +
                "@A #D @[Discard 1 spell from your hand]@: Target 1 of your opponent's SIGNI with power 5000 or less, and return it to their hand."
        );
        
		setName("zh_simplified", "必杀代号 自动门");
        setDescription("zh_simplified", 
                "@C :你的废弃区的魔法在3张以上时，这只精灵的力量+4000。\n" +
                "@A 横置从手牌把魔法1张舍弃:对战对手的力量5000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));

            registerActionAbility(new AbilityCostList(new DownCost(), new DiscardCost(new TargetFilter().spell())), this::onActionEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,5000)).get();
            addToHand(target);
        }
    }
}
