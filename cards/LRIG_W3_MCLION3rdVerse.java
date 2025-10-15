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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventAttack;

public final class LRIG_W3_MCLION3rdVerse extends Card {
    
    public LRIG_W3_MCLION3rdVerse()
    {
        setImageSets("WXDi-D04-004", "SPDi07-04","SPDi08-04");
        
        setOriginalName("MC.LION-3rdVerse");
        setAltNames("エムシーリオンサードウァ゛ース Emu Shii Rion Saado Vaasu");
        setDescription("jp",
                "=T ＜Card Jockey＞\n" +
                "^E：あなたのデッキの上からカードを４枚見る。その中からカードを２枚まで手札に加え、残りをトラッシュに置く。\n" +
                "@A $G1 %W0：ターン終了時まで、このルリグは@>@U：このルリグがアタックしたとき、そのアタック終了時、そのアタックによって対戦相手にダメージが与えられていなかった場合、あなたのアップ状態のレベル２のルリグ１体をダウンしてもよい。そうした場合、このルリグをアップする。@@を得る。"
        );
        
        setName("en", "MC LION - 3rd Verse");
        setDescription("en",
                "=T <<Card Jockey>>\n" +
                "^E: Look at the top four cards of your deck. Add up to two cards from among them into your hand and put the rest into your trash.\n" +
                "@A $G1 %W0: This LRIG gains@>@U: Whenever this LRIG attacks, at the end of the attack, if your opponent didn't take any damage by the attack, you may down an upped level two LRIG on your field. If you do, up this LRIG.@@until end of turn."
        );
        
        setName("en_fan", "MC.LION - 3rd Verse");
        setDescription("en_fan",
                "=T <<Card Jockey>>\n" +
                "^E: Look at the top 4 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest into the trash.\n" +
                "@A $G1 %W0: Until end of turn, this LRIG gains:" +
                "@>@U: Whenever this LRIG attacks, at the end of the attack, if the attack did not damage your opponent, you may down 1 of your upped level 2 LRIGs. If you do, up this LRIG."
        );
        
		setName("zh_simplified", "MC.LION-3rdVerse");
        setDescription("zh_simplified", 
                "=T<<Card:Jockey>>\n" +
                "^E:从你的牌组上面看4张牌。从中把牌2张最多加入手牌，剩下的放置到废弃区。\n" +
                "@A $G1 %W0:直到回合结束时为止，这只分身得到\n" +
                "@>@U 当这只分身攻击时，那次攻击结束时，没有因为那次攻击给予对战对手伤害的场合，可以把你的竖直状态的等级2的分身1只#D。这样做的场合，这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            trash(getCardsInLooked(getOwner()));
        }
        
        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            EventAttack eventAttack = (EventAttack)getEvent();
            callDelayedEffect(eventAttack.requestPostAttackTrigger(), () -> {
                if(!eventAttack.didAttackDealDamage())
                {
                    CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.DOWN).own().anyLRIG().withLevel(2).upped()).get();
                    
                    if(target != null)
                    {
                        down(target);
                        up();
                    }
                }
            });
        }
    }
}
