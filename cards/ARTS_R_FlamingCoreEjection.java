package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class ARTS_R_FlamingCoreEjection extends Card {

    public ARTS_R_FlamingCoreEjection()
    {
        setImageSets("WX25-P1-038");

        setOriginalName("本丸火出");
        setAltNames("ホンマルカシュツ Honmaru Kashutsu");
        setDescription("jp",
                "対戦相手のパワー12000以下のシグニ１体を対象とし、対戦相手が%X %X %Xを支払わないかぎり、それをバニッシュする。"
        );

        setName("en", "Flaming Core Ejection");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and banish it unless your opponent pays %X %X %X."
        );

		setName("zh_simplified", "本丸火出");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，如果对战对手不把%X %X %X:支付，那么将其破坏。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.ATTACK);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && !payEner(getOpponent(), Cost.colorless(3)))
            {
                banish(target);
            }
        }
    }
}

