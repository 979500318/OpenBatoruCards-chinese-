package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_MahomahoFessoneNaturalSource extends Card {

    public SIGNI_K1_MahomahoFessoneNaturalSource()
    {
        setImageSets("WXDi-P14-066");

        setOriginalName("羅原　まほまほ//フェゾーネ");
        setAltNames("ラゲンマホマホフェゾーネ Ragen Mahomaho Fezoone");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にレベル３の覚醒状態のシグニがある場合、対戦相手が手札を１枚捨てないかぎり、対戦相手のデッキの上からカードを４枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Mahomaho//Fesonne, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a level three awakened SIGNI on your field, put the top four cards of your opponent's deck into their trash unless your opponent discards a card." +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Mahomaho//Fessone, Natural Source");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is a level 3 awakened SIGNI on your field, put the top 4 cards of your opponent's deck into the trash unless your opponent discards 1 card from their hand." +
               "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "罗原 真帆帆//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有等级3的觉醒状态的精灵的场合，如果对战对手不把手牌1张舍弃，那么从对战对手的牌组上面把4张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withLevel(3).withState(CardStateFlag.AWAKENED).getValidTargetsCount() > 0 &&
               discard(getOpponent(), 0,1).get() == null)
            {
                millDeck(getOpponent(), 4);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
