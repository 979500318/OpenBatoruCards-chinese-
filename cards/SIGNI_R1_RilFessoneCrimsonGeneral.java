package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_R1_RilFessoneCrimsonGeneral extends Card {

    public SIGNI_R1_RilFessoneCrimsonGeneral()
    {
        setImageSets("WXDi-P16-065");

        setOriginalName("紅将　リル//フェゾーネ");
        setAltNames("コウショウリルフェゾーネ Koushou Riru Fezoone");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンに対戦相手のライフクロスが２枚以上クラッシュされていた場合、【エナチャージ１】をする。\n" +
                "@A $T1 #C：対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Ril//Fesonne, Crimson General");
        setDescription("en",
                "@U: At the end of your turn, if two or more of your opponent's Life Cloth were crushed this turn, [[Ener Charge 1]].\n@A $T1 #C: Vanish target SIGNI on your opponent's field with power 2000 or less." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Ril//Fessone, Crimson General");
        setDescription("en_fan",
                "@U: At the end of your turn, if 2 or more of your opponent's life cloth were crushed this turn, [[Ener Charge 1]].\n" +
                "@A $T1 #C: Target 1 of your opponent's SIGNI with power 2000 or less, and banish it." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "红将 莉露//音乐节");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合对战对手的生命护甲2张以上被击溃过的场合，[[能量填充1]]。\n" +
                "@A $T1 #C:对战对手的力量2000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new CoinCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.CRUSH && !isOwnCard(event.getCaller())) >= 2)
            {
                enerCharge(1);
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
            banish(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
