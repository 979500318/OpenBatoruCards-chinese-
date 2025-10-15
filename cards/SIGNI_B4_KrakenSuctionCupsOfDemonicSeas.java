package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B4_KrakenSuctionCupsOfDemonicSeas extends Card {

    public SIGNI_B4_KrakenSuctionCupsOfDemonicSeas()
    {
        setImageSets("WXK01-082");

        setOriginalName("魔海の吸盤　クラーケン");
        setAltNames("マカイノキュウバンクラーケン Makai no Kyuuban Kuraaken");
        setDescription("jp",
                "@U $T2：あなたが手札を１枚捨てたとき、対戦相手のシグニ１体を対象とし、あなたのターンの場合、ターン終了時まで、それのパワーを－4000する。"
        );

        setName("en", "Kraken, Suction Cups of Demonic Seas");
        setDescription("en",
                "@U $T2: When you discard a card, target 1 of your opponent's SIGNI, and if it is your turn, until end of turn, it gets --4000 power."
        );

		setName("zh_simplified", "魔海的吸盘 克拉肯");
        setDescription("zh_simplified", 
                "@U $T2 :当你把手牌1张舍弃时，对战对手的精灵1只作为对象，你的回合的场合，直到回合结束时为止，其的力量-4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(4);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null && isOwnTurn())
            {
                gainPower(target, -4000, ChronoDuration.turnEnd());
            }
        }
    }
}
