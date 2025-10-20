package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_K3_AlfouDissonaWickedDevilPrincess extends Card {

    public SIGNI_K3_AlfouDissonaWickedDevilPrincess()
    {
        setImageSets("WXDi-P13-053", "WXDi-P13-053P");

        setOriginalName("凶魔姫　アルフォウ//ディソナ");
        setAltNames("キョウマキアルフォウディソナ Kyoumaki Arufou Disona");
        setDescription("jp",
                "@U：あなたのメインフェイズ以外でこのシグニが場を離れたとき、対戦相手のデッキの上からカードを４枚トラッシュに置く。\n" +
                "@A @[アップ状態のルリグ２体をダウンする]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Alfou//Dissona, Doomed Evil Queen");
        setDescription("en",
                "@U: When this SIGNI leaves the field outside of your main phase, put the top four cards of your opponent's deck into their trash.\n@A @[Down two upped LRIG]@: Target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Alfou//Dissona, Wicked Devil Princess");
        setDescription("en_fan",
                "@U: When this SIGNI leaves the field other than during your main phase, put the top 4 cards of your opponent's deck into the trash.\n" +
                "@A @[Down 2 of your upped LRIG]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "凶魔姬 阿尔芙//失调");
        setDescription("zh_simplified", 
                "@U :当在你的主要阶段以外把这只精灵离场时，从对战对手的牌组上面把4张牌放置到废弃区。\n" +
                "@A 竖直状态的分身2只横置:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return (!isOwnTurn() || getCurrentPhase() != GamePhase.MAIN) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            millDeck(getOpponent(), 4);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}

