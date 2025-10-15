package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_VidofnirHolyAngel extends Card {

    public SIGNI_W1_VidofnirHolyAngel()
    {
        setImageSets("WX24-P2-059");

        setOriginalName("聖天　ヴィゾフニル");
        setAltNames("セイテンヴィゾフニル Seiten Vuizofuniru Vidofnir Vidopnir");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは[[シャドウ（レベル２以下のシグニ）]]を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Víðófnir, Holy Angel");
        setDescription("en",
                "@U: At the end of your turn, target 1 of your other SIGNI, and until the end of your opponent's next turn, it gains [[Shadow (level 2 or lower SIGNI)]]." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "圣天 维德佛尔尼尔");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，你的其他的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗影（等级2以下的精灵）]]。:（这只精灵不会被对战对手的等级2以下的精灵作为对象）" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().except(getCardIndex())).get();
            if(target != null) attachAbility(target, new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
