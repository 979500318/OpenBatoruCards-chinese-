package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class ARTS_R_RecollectionOfBathingInFlames extends Card {

    public ARTS_R_RecollectionOfBathingInFlames()
    {
        setImageSets("WX24-P1-004", "WX24-P1-004U");

        setOriginalName("追憶浸火");
        setAltNames("ツイオクシンカ Tsuioku Shinka");
        setDescription("jp",
                "対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "&E４枚以上@0追加で対戦相手のシグニ１体を対象とする。このターン、それがアタックしたとき、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Recollection of Bathing in Flames");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "&E4 or more@0 Additionally, target 1 of your opponent's SIGNI. This turn, whenever it attacks, you may pay %X. If you do, banish it."
        );

        setName("zh_simplified", "追忆浸火");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "&E4张以上@0追加对战对手的精灵1只作为对象。这个回合，当其攻击时，可以支付%X。这样做的场合，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }
        
        private CardIndex targetAttack;
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);

            if(getAbility().isRecollectFulfilled())
            {
                target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                
                if(target != null)
                {
                    targetAttack = target;
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachedAuto.setCondition(this::onAttachedAutoEffCond);
                    
                    GFX.attachToAbility(attachedAuto, new GFXCardTextureLayer(target, "burning_star"));
                    
                    attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return caller == targetAttack ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(payEner(Cost.colorless(1)))
            {
                banish(caller);
            }
        }
    }
}

