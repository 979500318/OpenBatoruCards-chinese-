package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIGA_K2_GuzukoDoublePeace extends Card {

    public LRIGA_K2_GuzukoDoublePeace()
    {
        setImageSets("WXDi-P14-038");

        setOriginalName("グズ子～ダブルピース～");
        setAltNames("グズコダブルピース Guzuko Daburu Piisu");
        setDescription("jp",
                "@E @[シグニ１体を場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X %X：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Guzuko ~Peace!~");
        setDescription("en",
                "@E Put a SIGNI on your field into its owner's trash: Vanish target SIGNI on your opponent's field.\n@E %X %X: Add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Guzuko~Double Peace~");
        setDescription("en_fan",
                "@E @[Put 1 SIGNI from your field into the trash]@: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E %X %X: Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );

		setName("zh_simplified", "迟钝子～耶耶～");
        setDescription("zh_simplified", 
                "@E 精灵1只从场上放置到废弃区:对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %X %X从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
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

            registerEnterAbility(new TrashCost(new TargetFilter().own().SIGNI()), this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
