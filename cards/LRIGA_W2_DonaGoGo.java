package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W2_DonaGoGo extends Card {

    public LRIGA_W2_DonaGoGo()
    {
        setImageSets("WXDi-P09-029");

        setOriginalName("ドーナ『いけいけ！』");
        setAltNames("ドーナイケイケ Doona Ike Ike");
        setDescription("jp",
                "@E：対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E：あなたのトラッシュから[ガード]を持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Dona \"Go, Go!\"");
        setDescription("en",
                "@E: Return target SIGNI with power 8000 or less on your opponent's field to its owner's hand.\n" +
                "@E: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Dona \"Go Go!\"");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand.\n" +
                "@E: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "多娜『冲刺！』");
        setDescription("zh_simplified", 
                "@E :对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。\n" +
                "@E 从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DONA);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            addToHand(target);
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(GameConst.CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
