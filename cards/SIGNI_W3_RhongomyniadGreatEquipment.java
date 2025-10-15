package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W3_RhongomyniadGreatEquipment extends Card {
    
    public SIGNI_W3_RhongomyniadGreatEquipment()
    {
        setImageSets("WXDi-P04-051");
        
        setOriginalName("大装　ロンゴミニアド");
        setAltNames("タイソウロンゴミニアド Taisou Rongominiado");
        setDescription("jp",
                "@U：あなたのルリグ１体がアタックしたとき、あなたのアップ状態の白のシグニ３体をダウンし%Wを支払ってもよい。そうした場合、そのルリグをアップし、ターン終了時まで、そのルリグは能力を失う。"
        );
        
        setName("en", "Rhongomyniad, Full Armed");
        setDescription("en",
                "@U: Whenever a LRIG on your field attacks, you may down three upped white SIGNI on your field and pay %W. If you do, up the LRIG that attacked. It loses it's abilities until end of turn."
        );
        
        setName("en_fan", "Rhongomyniad, Great Equipment");
        setDescription("en_fan",
                "@U: Whenever your LRIG attacks, you may down 3 of your upped white SIGNI, and pay %W. If you do, up that LRIG, and until end of turn, it loses its abilities."
        );
        
		setName("zh_simplified", "大装 圣枪伦戈米尼亚德");
        setDescription("zh_simplified", 
                "@U 当你的分身1只攻击时，可以把你的竖直状态的白色的精灵3只#D并支付%W。这样做的场合，那只分身竖直，直到回合结束时为止，那只分身的能力失去。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.DOWN).own().SIGNI().upped().withColor(CardColor.WHITE));
            
            if(data.size() == 3 && down(data) == 3 && payEner(Cost.color(CardColor.WHITE, 1)))
            {
                up(caller);
                disableAllAbilities(caller, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
    }
}
