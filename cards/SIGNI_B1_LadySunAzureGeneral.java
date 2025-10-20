package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_LadySunAzureGeneral extends Card {

    public SIGNI_B1_LadySunAzureGeneral()
    {
        setImageSets("WX25-P2-081");

        setOriginalName("蒼将　ソンショウコウ");
        setAltNames("ソウショウソンショウコウ Soushou Sonshoukou");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを凍結する。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Lady Sun, Azure General");
        setDescription("en",
                "@E: Target 1 of your opponent's SIGNI, and freeze it." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "苍将 孙尚香");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其冻结。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
