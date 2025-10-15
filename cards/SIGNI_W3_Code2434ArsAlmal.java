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
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_Code2434ArsAlmal extends Card {
    
    public SIGNI_W3_Code2434ArsAlmal()
    {
        setImageSets("WXDi-P00-033");
        
        setOriginalName("コード２４３４　アルス・アルマル");
        setAltNames("コードニジサンジアルスアルマル Koodo Nijisanji Arusu Arumaru");
        setDescription("jp",
                "=T ＜さんばか＞\n" +
                "^U：このシグニがアタックしたとき、このシグニのパワーが２００００以上の場合、対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それを手札に戻す。\n" +
                "@A $T1 %W：あなたの手札から＜バーチャル＞のシグニを好きな枚数公開する。ターン終了時まで、このシグニのパワーをこの方法で公開したカード１枚につき＋１０００する。"
        );
        
        setName("en", "Ars Almal, Code 2434");
        setDescription("en",
                "=T <<Sanbaka>>\n" +
                "^U: Whenever this SIGNI attacks, if its power is 20000 or more, you may discard a card. If you do, return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@A $T1 %W: You may reveal any number of <<Virtual>> SIGNI from your hand. Until end of turn, this SIGNI gets +1000 power for each card revealed this way."
        );
        
        setName("en_fan", "Code 2434 Ars Almal");
        setDescription("en_fan",
                "=T <<Sanbaka>>\n" +
                "^U: Whenever this SIGNI attacks, if this SIGNI's power is 20000 or more, target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, return that SIGNI to their hand.\n" +
                "@A $T1 %W: Reveal any number of <<Virtual>> SIGNI from your hand. Until end of turn, this SIGNI gets +1000 power for each card revealed this way."
        );
        
		setName("zh_simplified", "2434代号 阿露丝·阿尔玛");
        setDescription("zh_simplified", 
                "=T<<さんばか>>\n" +
                "^U:当这只精灵攻击时，这只精灵的力量在20000以上的场合，对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其返回手牌。\n" +
                "@A $T1 %W:从你的手牌把<<バーチャル>>精灵任意张数公开。直到回合结束时为止，这只精灵的力量依据这个方法公开的牌的数量，每有1张就+1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.SANBAKA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getCardIndex().getIndexedInstance().getPower().getValue() >= 20000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                
                if(target != null && discard(0,1).get() != null)
                {
                    addToHand(target);
                }
            }
        }
        
        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.REVEAL).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromHand());
            
            if(data.get() != null)
            {
                reveal(data);
                
                gainPower(getCardIndex(), 1000 * data.size(), ChronoDuration.turnEnd());
                
                addToHand(data);
            }
        }
    }
}
