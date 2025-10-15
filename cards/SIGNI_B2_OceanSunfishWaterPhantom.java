package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_OceanSunfishWaterPhantom extends Card {

    public SIGNI_B2_OceanSunfishWaterPhantom()
    {
        setImageSets("WX24-P3-077");

        setOriginalName("幻水　マンボウ");
        setAltNames("ゲンスイマンボウ Gensui Manbou");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。" +
                "~#：対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Ocean Sunfish, Water Phantom");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down this SIGNI. If you do, draw 1 card." +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "幻水 翻车鱼");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，可以把竖直状态的这只精灵#D。这样做的场合，抽1张牌。" +
                "~#对战对手的分身1只作为对象，将其#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate())
            {
                down();
                
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
