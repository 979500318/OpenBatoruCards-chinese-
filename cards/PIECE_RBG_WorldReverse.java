package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class PIECE_RBG_WorldReverse extends Card {
    
    public PIECE_RBG_WorldReverse()
    {
        setImageSets("WXDi-P04-002");
        
        setOriginalName("世界逆流");
        setAltNames("ワールドリバース Waarudo Ribaasu");
        setDescription("jp",
                "=U =T ＜アンシエント・サプライズ＞\n\n" +
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@U $T1：あなたのターンの間、あなたのシグニ１体が場に出たとき、以下の３つから１つを選ぶ。\n" +
                "$$1場に出たそのシグニが赤の場合、対戦相手のパワー8000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "$$2場に出たそのシグニが青の場合、カードを１枚引く。\n" +
                "$$3場に出たそのシグニが緑の場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "World Reverse");
        setDescription("en",
                "=U =T <<Ancient Surprise>>\n\n" +
                "You gain the following ability for the duration of the game.\n" +
                "@>@U $T1: During your turn, when a SIGNI enters your field, choose one of the following.\n" +
                "$$1 If the SIGNI that enters the field is red, you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 8000 or less.\n" +
                "$$2 If the SIGNI that enters the field is blue, draw a card.\n" +
                "$$3 If the SIGNI that enters the field is green, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "World Reverse");
        setDescription("en_fan",
                "=U =T <<Ancient Surprise>>\n\n" +
                "This game, you gain the following ability:" +
                "@>@U $T1: During your turn, when 1 of your SIGNI enters the field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If that SIGNI that entered the field was red, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %X. If you do, banish it.\n" +
                "$$2 If that SIGNI that entered the field was blue, draw 1 card.\n" +
                "$$3 If that SIGNI that entered the field was green, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "世界逆流");
        setDescription("zh_simplified", 
                "=U=T<<アンシエント・サプライズ>>\n" +
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@U $T1 :你的回合期间，当你的精灵1只出场时，从以下的3种选1种。\n" +
                "$$1 出场的那只精灵是红色的场合，对战对手的力量8000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n" +
                "$$2 出场的那只精灵是蓝色的场合，抽1张牌。\n" +
                "$$3 出场的那只精灵是绿色的场合，[[能量填充1]]。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED, CardColor.BLUE, CardColor.GREEN);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ENTER, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            attachedAuto.enableEventSourceSelection();
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            switch(playerChoiceMode())
            {
                case 1<<0:
                {
                    if(caller.getIndexedInstance().getColor().matches(CardColor.RED))
                    {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                        
                        if(target != null && payEner(Cost.colorless(1)))
                        {
                            banish(target);
                        }
                    }
                    
                    break;
                }
                case 1<<1:
                {
                    if(caller.getIndexedInstance().getColor().matches(CardColor.BLUE))
                    {
                        draw(1);
                    }
                    
                    break;
                }
                case 1<<2:
                {
                    if(caller.getIndexedInstance().getColor().matches(CardColor.GREEN))
                    {
                        enerCharge(1);
                    }
                    
                    break;
                }
            }
        }
    }
}
