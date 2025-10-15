package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SIGNI_G3_KahoKuwakami extends Card {

    public SIGNI_G3_KahoKuwakami()
    {
        setImageSets("WXDi-CP02-092");

        setOriginalName("桑上カホ");
        setAltNames("クワカミカホ Kuwakami Kaho");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、【エナチャージ１】をする。" +
                "~{{E %G %G %X：ターン終了時まで、このシグニのパワーを＋10000し、このシグニは【Ｓランサー】を得る。@@" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Kuwakami Kaho");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If that card is <<Blue Archive>>, [[Ener Charge 1]].~{{E %G %G %X: This SIGNI gets +10000 power and gains [[S Lancer]] until end of turn.@@" +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Kaho Kuwakami");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If it is a <<Blue Archive>> card, [[Ener Charge 1]]." +
                "~{{E %G %G %X: Until end of turn, this SIGNI gets +10000 power, and it gains [[S Lancer]].@@" +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "桑上嘉穗");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，[[能量填充1]]。\n" +
                "~{{E%G %G%X:直到回合结束时为止，这只精灵的力量+10000，这只精灵得到[[S枪兵]]。（当持有[[S枪兵]]的精灵战斗把精灵破坏时，对战对手有生命护甲的场合，将其1张击溃。没有的场合，给予对战对手伤害）@@" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)), this::onActionEff);
            act.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();

            if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) || enerCharge(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onActionEff()
        {
            gainPower(getCardIndex(), 10000, ChronoDuration.turnEnd());
            
            attachAbility(getCardIndex(), new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
