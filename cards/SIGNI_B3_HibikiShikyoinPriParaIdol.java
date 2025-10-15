package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B3_HibikiShikyoinPriParaIdol extends Card {
    
    public SIGNI_B3_HibikiShikyoinPriParaIdol()
    {
        setImageSets("WXDi-P10-038");
        
        setOriginalName("プリパラアイドル　紫京院ひびき");
        setAltNames("プリパラアイドルシキョウインヒビキ Puripara Aidoru Shikyouin Hibiki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、カードを２枚引き、＜プリパラ＞のシグニを１枚捨てるか手札を２枚捨てる。\n" +
                "@U：このシグニがバニッシュされたとき、対戦相手は手札を１枚捨てる。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Shikyoin Hibiki, Pripara Idol");
        setDescription("en",
                "@U: At the beginning of your attack phase, draw two cards, and discard two cards unless you discard a <<Pripara>> SIGNI.\n" +
                "@U: When this SIGNI is vanished, your opponent discards a card." +
                "~#Choose one -- \n$$1 Down up to two target SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Hibiki Shikyoin, PriPara Idol");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, draw 2 cards, and discard 1 <<PriPara>> SIGNI or 2 cards from your hand.\n" +
                "@U: When this SIGNI is banished, your opponent discards 1 card from their hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "美妙天堂偶像 紫京院响");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，抽2张牌并，把<<プリパラ>>精灵1张舍弃或手牌2张舍弃。\n" +
                "@U :当这只精灵被破坏时，对战对手把手牌1张舍弃。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(2);
            
            pay(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.PRIPARA)), new DiscardCost(2));
        }
        
        private void onAutoEff2()
        {
            discard(getOpponent(), 1);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
