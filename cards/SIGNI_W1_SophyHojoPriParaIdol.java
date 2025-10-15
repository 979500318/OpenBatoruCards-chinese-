package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W1_SophyHojoPriParaIdol extends Card {

    public SIGNI_W1_SophyHojoPriParaIdol()
    {
        setImageSets("WXDi-P10-045");

        setOriginalName("プリパラアイドル　北条そふぃ");
        setAltNames("プリパラアイドルホウジョウソフィ Puripara Aidoru Houjou Sofi");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの手札から＜プリパラ＞のシグニを１枚公開してもよい。そうした場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Hojo Sophy, Pripara Idol");
        setDescription("en",
                "@U: At the end of your turn, you may reveal a <<Pripara>> SIGNI from your hand. If you do, this SIGNI gets +4000 power until the end of your opponent's next end phase." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Sophy Hojo, PriPara Idol");
        setDescription("en_fan",
                "@U: At the end of your turn, you may reveal 1 <<PriPara>> SIGNI from your hand. If you do, until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "美妙天堂偶像 北条索菲");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，可以从你的手牌把<<プリパラ>>精灵1张公开。这样做的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.REVEAL).own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromHand()).get();
            if(reveal(cardIndex))
            {
                gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
                addToHand(cardIndex);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
