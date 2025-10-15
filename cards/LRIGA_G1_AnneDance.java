package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_AnneDance extends Card {

    public LRIGA_G1_AnneDance()
    {
        setImageSets("WXDi-P11-034");

        setOriginalName("アン － 舞イ");
        setAltNames("アンマイ An Mai");
        setDescription("jp",
                "@E：対戦相手のパワー7000以上のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Ann - Dance");
        setDescription("en",
                "@E: Return target SIGNI with power 7000 or more on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Anne Dance");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 7000 or more, and return it to their hand."
        );

		setName("zh_simplified", "安 - 舞动");
        setDescription("zh_simplified", 
                "@E :对战对手的力量7000以上的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setLevel(1);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(7000,0)).get();
            addToHand(target);
        }
    }
}
