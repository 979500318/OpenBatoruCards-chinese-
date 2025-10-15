package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_MikomikoMemoriaNaturalBacteriaPrincess extends Card {

    public SIGNI_B3_MikomikoMemoriaNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P09-042", "WXDi-P09-042P");

        setOriginalName("羅菌姫　みこみこ//メモリア");
        setAltNames("ラキンヒメミコミコメモリア Rakinhime Mikomiko Memoria");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋3000それ、このシグニは@>@U：このシグニがバニッシュされたとき、対戦相手が手札を１枚捨てないかぎり、このシグニをエナゾーンからダウン状態で場に出す。@@を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。その後、対戦相手の手札が０枚の場合、このシグニは覚醒する。"
        );

        setName("en", "Mikomiko//Memoria, Bacteria Queen");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gets +3000 power and gains@>@U: When this SIGNI is vanished, put this SIGNI from your Ener Zone onto your field downed unless your opponent discards a card.@@" +
                "@U: At the beginning of your attack phase, your opponent discards a card. Then, if your opponent has no cards in their hand, this SIGNI is awakened."
        );
        
        setName("en_fan", "Mikomiko//Memoria, Natural Bacteria Princess");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, this SIGNI gets +3000 power, and it gains:" +
                "@>@U: When this SIGNI is banished, put this SIGNI from your ener zone onto the field downed unless your opponent discards 1 card from their hand.@@" +
                "@U: At the beginning of your attack phase, you opponent discards 1 card from their hand. Then, if there are 0 cards in your opponent's hand, this SIGNI awakens."
        );

		setName("zh_simplified", "罗菌姬 美琴琴//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+3000，这只精灵得到\n" +
                "@>@U 当这只精灵被破坏时，如果对战对手不把手牌1张舍弃，那么这张精灵从能量区以#D状态出场。@@\n" +
                "@U :你的攻击阶段开始时，对战对手把手牌1张舍弃。然后，对战对手的手牌在0张的场合，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000), new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && discard(getOpponent(), 0,1).get() == null)
            {
                putOnField(getCardIndex(), Enter.DOWNED);
            }
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
            
            if(getHandCount(getOpponent()) == 0)
            {
                getCardStateFlags().addValue(CardStateFlag.AWAKENED);
            }
        }
    }
}
