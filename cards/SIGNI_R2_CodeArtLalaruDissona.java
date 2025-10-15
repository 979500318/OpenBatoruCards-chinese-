package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_CodeArtLalaruDissona extends Card {

    public SIGNI_R2_CodeArtLalaruDissona()
    {
        setImageSets("WXDi-P12-066", "SPDi27-02","SPDi38-06");

        setOriginalName("コードアート　ララ・ルー//ディソナ");
        setAltNames("コードアートララルーディソナ Koodo Aato Rararuu Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがスペルを使用していた場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Lala Ru//Dissona, Code: Art");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have used a spell this turn, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Code Art Lalaru//Dissona");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you used a spell this turn, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "必杀代号 啦啦·噜//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把魔法使用过的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
                trash(target);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
