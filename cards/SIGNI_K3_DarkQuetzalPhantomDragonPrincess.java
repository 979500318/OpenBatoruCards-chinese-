package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_K3_DarkQuetzalPhantomDragonPrincess extends Card {
    
    public SIGNI_K3_DarkQuetzalPhantomDragonPrincess()
    {
        setImageSets("WXDi-P08-048");
        
        setOriginalName("幻竜姫　ダークゲツァル");
        setAltNames("ゲンリュウキダークゲツァル Genryuuki Daaku Getsaru");
        setDescription("jp",
                "@C：このシグニは下にレベル１、レベル２、レベル３のシグニがそれぞれ１枚以上あるかぎり、@>@Uこのシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。@@を得る。それぞれ２枚以上あるかぎり、追加で【アサシン】を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのデッキをシャッフルし上からカード４枚をこのシグニの下に置く。"
        );
        
        setName("en", "Dark Quetzal, Phantom Dragon Queen");
        setDescription("en",
                "@C: As long as there is at least one level one, one level two, and one level three SIGNI underneath this SIGNI, it gains@>@U: Whenever this SIGNI attacks, target SIGNI on your opponent's field gets --12000 power until end of turn.@@As long as there are two or more of each level, it also gains [[Assassin]].\n" +
                "@U: At the beginning of your attack phase, shuffle your deck and put the top four cards under this SIGNI. "
        );
        
        setName("en_fan", "Dark Quetzal, Phantom Dragon Princess");
        setDescription("en_fan",
                "@C: As long as there is a level 1, a level 2, and a level 3 SIGNI each under this SIGNI, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power.@@" +
                "As long as there are 2 or more SIGNI of each of those levels, it additionally gains [[Assassin]].\n" +
                "@U: At the beginning of your attack phase, shuffle your deck, and put the top 4 cards of your deck under this SIGNI."
        );
        
		setName("zh_simplified", "幻龙姬 黑暗羽蛇神");
        setDescription("zh_simplified", 
                "@C :这只精灵的下面有等级1、等级2、等级3的精灵各1张以上时，得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。@@\n" +
                "。各2张以上时，追加得到[[暗杀]]。\n" +
                "@U :你的攻击阶段开始时，你的牌组洗切从上面把4张牌放置到这只精灵的下面。（表向放置）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffMod1GetSample), new AbilityGainModifier(this::onConstEffMod2Cond, this::onConstEffMod2GetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            for(int i=1;i<=3;i++) if(new TargetFilter().own().SIGNI().withLevel(i).under(getCardIndex()).getValidTargetsCount() < 1) return ConditionState.BAD;
            
            return ConditionState.OK;
        }
        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -12000, ChronoDuration.turnEnd());
        }
        private ConditionState onConstEffMod2Cond()
        {
            for(int i=1;i<=3;i++) if(new TargetFilter().own().SIGNI().withLevel(i).under(getCardIndex()).getValidTargetsCount() < 2) return ConditionState.BAD;
            
            return ConditionState.OK;
        }
        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            shuffleDeck();
            
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC, 4);
        }
    }
}
