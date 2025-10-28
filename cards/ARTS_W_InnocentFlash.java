package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class ARTS_W_InnocentFlash extends Card {

    public ARTS_W_InnocentFlash()
    {
        setImageSets("WX24-P1-016");

        setOriginalName("イノセント・フラッシュ");
        setAltNames("イノセントフラッシュ Inosento Furasshu");
        setDescription("jp",
                "ターン終了時まで、対戦相手のすべてのシグニは能力を失う。その後、対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Innocent Flash");
        setDescription("en",
                "Until end of turn, all of your opponent's SIGNI lose their abilities. Then, target 1 of your opponent's SIGNI, and return it to their hand."
        );

        setName("es", "Brillo de inocencia");
        setDescription("es",
                "Hasta el final del turno, todas tus SIGNI pierden sus habilidades. Entonces, selecciona 1 SIGNI oponente y devuelvela a la mano."
        );

        setName("zh_simplified", "纯真·闪光");
        setDescription("zh_simplified", 
                "直到回合结束时为止，对战对手的全部的精灵的能力失去。然后，对战对手的精灵1只作为对象，将其返回手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

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
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
    }
}

