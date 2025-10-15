package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K2_ZweiMamushi extends Card {
    
    public SIGNI_K2_ZweiMamushi()
    {
        setImageSets("WXDi-P08-079");
        
        setOriginalName("ツヴァイ＝マムシ");
        setAltNames("ツヴァイマムシ Tsuvai Mamushi");
        setDescription("jp",
                "@U $T1：あなたのターンの間、コストか効果１つによってあなたのデッキからカードが合計２枚以上トラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );
        
        setName("en", "Mamushi Type: Zwei");
        setDescription("en",
                "@U $T1: During your turn, when a total of two or more cards are put from your deck into your trash by a cost or an effect, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Zwei-Mamushi");
        setDescription("en_fan",
                "@U $T1: During your turn, when 2 or more cards are put from your deck into the trash by a cost or a single effect, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );
        
		setName("zh_simplified", "ZWEI=日本蝮");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当因为费用或效果1个从你的牌组把牌合计2张以上放置到废弃区时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && getEvent().isAtOnce(2) && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
