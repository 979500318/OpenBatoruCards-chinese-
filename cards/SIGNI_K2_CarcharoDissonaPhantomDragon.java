package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public class SIGNI_K2_CarcharoDissonaPhantomDragon extends Card {

    public SIGNI_K2_CarcharoDissonaPhantomDragon()
    {
        setImageSets("WXDi-P13-087", "SPDi01-112");

        setOriginalName("幻竜　カルカロ//ディソナ");
        setAltNames("ゲンリュウカルカロディソナ Genryuu Karukaro Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの上からカードを３枚トラッシュに置く。その後、%Kを支払ってもよい。そうした場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Carcharo//Dissona, Phantom Dragon");
        setDescription("en",
                "@U: At the beginning of your attack phase, put the top three cards of your deck into your trash. Then, you may pay %K. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Carcharo//Dissona, Phantom Dragon");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, put the top 3 cards of your deck into the trash. Then, you may pay %K. If you do, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "幻龙 鲨齿龙//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从你的牌组上面把3张牌放置到废弃区。然后，可以支付%K。这样做的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            millDeck(3);
            
            if(payEner(Cost.color(CardColor.BLACK, 1)))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
