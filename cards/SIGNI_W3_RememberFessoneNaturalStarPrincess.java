package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_W3_RememberFessoneNaturalStarPrincess extends Card {

    public SIGNI_W3_RememberFessoneNaturalStarPrincess()
    {
        setImageSets("WXDi-P14-040", "WXDi-P14-040P");

        setOriginalName("羅星姫　リメンバ//フェゾーネ");
        setAltNames("ラセイキリメンバフェゾーネ Raseiki Rimenba Fezoone");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手の場に凍結状態のルリグとシグニが合計３体以上いる場合、%X %X %Xを支払ってもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。\n" +
                "@E：センタールリグではない対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "@A $T1 @[アップ状態のシグニ１体をダウンする]@：対戦相手のシグニ１体を対象とし、それを凍結する。"
        );

        setName("en", "Remember//Fesonne, Galactic Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are a total of three or more frozen LRIG and SIGNI on your opponent's field, you may pay %X %X %X. If you do, this SIGNI gains [[Assassin]] until end of turn.\n@E: Freeze target LRIG on your opponent's field that is not their Center LRIG.\n@A $T1 @[Down an upped SIGNI]@: Freeze target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Remember//Fessone, Natural Star Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 3 or more frozen LRIG and SIGNI on your opponent's field, you may pay %X %X %X. If you do, until end of turn, this SIGNI gains [[Assassin]].\n" +
                "@E: Target 1 of your opponent's non-center LRIG, and freeze it.\n" +
                "@A $T1 @[Down 1 of your upped SIGNI]@: Target 1 of your opponent's SIGNI, and freeze it."
        );

		setName("zh_simplified", "罗星姬 忆//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的场上的冻结状态的分身和精灵合计3只以上的场合，可以支付%X %X %X。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n" +
                "@E :不是核心分身的对战对手的分身1只作为对象，将其冻结。\n" +
                "@A $T1 竖直状态的精灵1只#D:对战对手的精灵1只作为对象，将其冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(10000);

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

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new DownCost(new TargetFilter().SIGNI()), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private void onAutoEff()
        {
            if((new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() +
                new TargetFilter().OP().anyLRIG().withState(CardStateFlag.FROZEN).getValidTargetsCount()) >= 3 && payEner(Cost.colorless(3)))
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG().except(getLRIG(getOpponent()))).get();
            freeze(target);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }
    }
}
